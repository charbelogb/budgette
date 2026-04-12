package com.budgette.backend.application.controller;

import com.budgette.backend.application.dto.response.DashboardResponse;
import com.budgette.backend.application.mapper.WebMapper;
import com.budgette.backend.domain.port.in.GetDashboardUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard", description = "Tableau de bord financier")
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {

    private final GetDashboardUseCase getDashboardUseCase;
    private final WebMapper webMapper;

    public DashboardController(GetDashboardUseCase getDashboardUseCase, WebMapper webMapper) {
        this.getDashboardUseCase = getDashboardUseCase;
        this.webMapper = webMapper;
    }

    @GetMapping
    @Operation(summary = "Obtenir le tableau de bord de l'utilisateur connecté")
    public ResponseEntity<DashboardResponse> getDashboard(Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        GetDashboardUseCase.DashboardResult result = getDashboardUseCase.getDashboard(userId);
        return ResponseEntity.ok(webMapper.toDashboardResponse(result));
    }
}
