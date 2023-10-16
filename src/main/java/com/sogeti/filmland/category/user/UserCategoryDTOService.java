package com.sogeti.filmland.category.user;

import com.sogeti.filmland.category.Category;
import com.sogeti.filmland.category.CategoryRepository;
import com.sogeti.filmland.category.CategoryTypeConverter;
import com.sogeti.filmland.category.user.subscription.UserCategorySubscriptionDTO;
import com.sogeti.filmland.category.user.subscription.UserCategorySubscriptionDTOConverter;
import com.sogeti.filmland.category.user.subscription.shared.SharedSubscription;
import com.sogeti.filmland.category.user.subscription.shared.SharedSubscriptionDTO;
import com.sogeti.filmland.category.user.subscription.shared.SharedSubscriptionRepository;
import com.sogeti.filmland.security.AuthenticationInfo;
import com.sogeti.filmland.user.User;
import com.sogeti.filmland.user.UserRepository;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserCategoryDTOService {

    private final CategoryRepository categoryRepository;

    private final SharedSubscriptionRepository sharedSubscriptionRepository;

    private final UserCategoryRepository userCategoryRepository;

    private final UserRepository userRepository;

    private final UserCategorySubscriptionDTOConverter subscriptionDTOConverter =
            new UserCategorySubscriptionDTOConverter();

    private final UserCategoriesDTOConverter userCategoriesDTOConverter = new UserCategoriesDTOConverter();

    private final CategoryTypeConverter categoryTypeConverter = new CategoryTypeConverter();

    @Autowired
    public UserCategoryDTOService(CategoryRepository categoryRepository,
                                  SharedSubscriptionRepository sharedSubscriptionRepository,
                                  UserCategoryRepository userCategoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.sharedSubscriptionRepository = sharedSubscriptionRepository;
        this.userCategoryRepository = userCategoryRepository;
        this.userRepository = userRepository;
    }


    public UserCategoriesDTO getAllForCustomer() {
        User user = findCurrentUser();
        List<UserCategory> availableCategories = userCategoryRepository.findAllByUserIdAndStartDateIsNull(user.getId());
        List<UserCategory> subscriptions = userCategoryRepository.findAllByUserIdAndStartDateNotNull(user.getId());

        return userCategoriesDTOConverter.toDTO(availableCategories, subscriptions);
    }

    public int subscribeToCategory(UserCategorySubscriptionDTO dto) {
        Category category = categoryRepository.findByName(categoryTypeConverter.convertToEntityAttribute(dto.getName()))
                .orElseThrow(EntityNotFoundException::new);
        dto.setCategoryId(category.getId());

        User user = findCurrentUser();

        dto.setUserId(user.getId());
        dto.setRemainingContent(category.getAvailableContent());

        UserCategory userCategory = getAvailableUserCategory(dto);
        UserCategory entity = subscriptionDTOConverter.mergeToEntity(userCategory, dto);

        UserCategory savedEntity = userCategoryRepository.save(entity);
        return savedEntity.getId();
    }

    public int shareCategory(SharedSubscriptionDTO dto) {
        SharedSubscription entity = new SharedSubscription();
        User user = findCurrentUser();
        Category category = categoryRepository.findByName(categoryTypeConverter.convertToEntityAttribute(dto.getName()))
                .orElseThrow(EntityNotFoundException::new);

        UserCategory subscriptionA = userCategoryRepository
                .findByUserIdAndCategoryIdAndStartDateNotNull(user.getId(),category.getId())
                .orElseThrow(EntityNotFoundException::new);

        User userToShare = userRepository.findByEmailIgnoreCase(dto.getEmail())
                .orElseThrow(EntityNotFoundException::new);

        if(Objects.equals(userToShare.getId(), user.getId())) {
            throw new UnsupportedOperationException("Cannot share a subscription with yourself!");
        }

        Optional<UserCategory> boxedSubscriptionB = userCategoryRepository
                .findByUserIdAndCategoryIdAndStartDateNotNull(userToShare.getId(), category.getId());
        UserCategory subscriptionB;

        if(boxedSubscriptionB.isPresent()) {
            subscriptionB = boxedSubscriptionB.get();
        } else {
            throw new EntityExistsException("The user you want to share subscriptions with needs to subscribe first.");
        }

        // Turning the parameters around prevents the accidental creation of 2 shared subscriptions.

        if(subscriptionA.isShared() || subscriptionB.isShared()) {
            throw new EntityExistsException("There already is a shared subscription");
        }

        subscriptionA.setRemainingContent(subscriptionA.getRemainingContent() / 2);
        subscriptionB.setRemainingContent(subscriptionB.getRemainingContent() / 2);
        entity.setStartDate(LocalDate.now());
        subscriptionA.setShared(true);
        subscriptionB.setShared(true);
        entity.setSubscriptionA(subscriptionA);
        entity.setSubscriptionB(subscriptionB);

        return sharedSubscriptionRepository.save(entity).getId();
    }

    private UserCategory getAvailableUserCategory(UserCategorySubscriptionDTO dto) {
        User user = findCurrentUser();
        dto.setUserId(user.getId());
        UserCategory userCategory;

        try {
            userCategory = userCategoryRepository
                    .findByUserIdAndCategoryIdAndStartDateIsNull(dto.getUserId(), dto.getCategoryId())
                    .orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException exception) {
            Optional<UserCategory> subscribedCategory =
                    userCategoryRepository
                            .findByUserIdAndCategoryIdAndStartDateNotNull(dto.getUserId(), dto.getCategoryId());

            if(subscribedCategory.isPresent()) {
                throw new EntityExistsException(
                        String.format("You already have a subscription on this category, your subscription " +
                                "started on %s.", subscribedCategory.get().getStartDate()));
            } else {
                throw new EntityNotFoundException("Category not found in available or subscribed categories");
            }
        }
        return userCategory;
    }

    private User findCurrentUser() {
        Optional<User> user = userRepository.findByEmailIgnoreCase(AuthenticationInfo.getCurrentUserEmail());
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("No signed in user found");
        }
    }
}
