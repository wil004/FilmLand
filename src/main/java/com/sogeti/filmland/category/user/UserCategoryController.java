package com.sogeti.filmland.category.user;

import com.sogeti.filmland.category.user.subscription.UserCategorySubscriptionDTO;
import com.sogeti.filmland.category.user.subscription.shared.SharedSubscriptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class UserCategoryController {

    private final UserCategoryDTOService dtoService;

    @Autowired
    public UserCategoryController(UserCategoryDTOService dtoService) {
        this.dtoService = dtoService;
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public UserCategoriesDTO getAllUserCategories() {
        return dtoService.getAllForCustomer();
    }

    @PostMapping("/subscribe")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Object>  subscribeToCategory(@RequestBody UserCategorySubscriptionDTO dto) {
        int id = dtoService.subscribeToCategory(dto);

        return ResponseEntity.ok()
                .body(String.format("UserSubscription created id: %d successfully subscribed to category: %s",
                        id, dto.getName()));
    }

    @PostMapping("/share")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Object> shareCategory(@RequestBody SharedSubscriptionDTO dto) {
        int id = dtoService.shareCategory(dto);

        return ResponseEntity.ok()
                .body(String.format("UserSubscriptionUser created id: %d successfully shared category with user: %s",
                        id, dto.getEmail()));
    }
}
