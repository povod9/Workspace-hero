package com.workspace.hero.booking_service.Entity;

import com.workspace.hero.booking_service.Entity.enums.WorkSpaceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "workspace")
public class WorkspaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkSpaceType type;

    @Column(nullable = false)
    private BigDecimal pricePerHour;

    public WorkspaceEntity() {
    }

    public WorkspaceEntity(Long id, String name, WorkSpaceType type, BigDecimal pricePerHour) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.pricePerHour = pricePerHour;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WorkSpaceType getType() {
        return type;
    }

    public void setType(WorkSpaceType type) {
        this.type = type;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
