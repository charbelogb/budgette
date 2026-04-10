package com.budgette.backend.infrastructure.web.controller;

import com.budgette.backend.domain.port.in.GetDashboardUseCase;
import com.budgette.backend.infrastructure.web.dto.response.DashboardResponse;
import com.budgette.backend.infrastructure.web.mapper.WebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "Données consolidées du tableau de bord")
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {

    private final GetDashboardUseCase getDashboardUseCase;
    private final WebMapper webMapper;

    public DashboardController(GetDashboardUseCase getDashboardUseCase, WebMapper webMapper) {
        this.getDashboardUseCase = getDashboardUseCase;
        this.webMapper = webMapper;
    }

    @GetMapping
    @Operation(summary = "Obtenir les données du dashboard")
    public ResponseEntity<DashboardResponse> getDashboard(
            @AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();
        GetDashboardUseCase.DashboardData data = getDashboardUseCase.getDashboard(userId);

        DashboardResponse response = new DashboardResponse(
                data.totalBalance(),
                "XOF",
                data.accounts().stream().map(webMapper::toAccountResponse).toList(),
                data.recentTransactions().stream().map(webMapper::toTransactionResponse).toList()
        );

        return ResponseEntity.ok(response);
    }
}
