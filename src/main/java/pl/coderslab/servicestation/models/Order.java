package pl.coderslab.servicestation.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.servicestation.validationGroups.CancelledOrderGroup;
import pl.coderslab.servicestation.validationGroups.CreatedOrderGroup;
import pl.coderslab.servicestation.validationGroups.FinishedOrderGroup;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    @NotNull
    @NotBlank
    @Column(name = "title")
    private String title;

    @NotNull
    @Size(min = 10, max = 10000)
    @Column(name = "initial_diagnosis", columnDefinition = "text")
    private String initialDiagnosis;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "planned_repair_start")
    private LocalDate plannedRepairStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "actual_repair_start")
    private LocalDate actualRepairStart;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<Part> parts;

    @NotNull(groups = {FinishedOrderGroup.class}, message = "Finished repair must have price")
    @Min(value = 1, groups = {FinishedOrderGroup.class})
    @Column(name = "price_of_service")
    private Double priceOfService;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<Invoice> invoices;

    @Column(name = "created")
    private LocalDate created;

    @Column(name = "updated")
    private LocalDate updated;

    @NotNull(message = "Report is required", groups = {FinishedOrderGroup.class})
    @Size(min = 100, max = 10000, message = "min 100 and max 10000 characters", groups = {FinishedOrderGroup.class})
    @Column(name = "repair_progress_report", columnDefinition = "text")
    private String repairProgressReport;

    @NotNull(groups = {CancelledOrderGroup.class})
    @Size(min = 10, max = 10000, message = "min 10 and max 10000 characters", groups = {CancelledOrderGroup.class})
    @Column(name = "cancel_reason", columnDefinition = "text")
    private String cancelReason;

    @NotNull(groups = {CreatedOrderGroup.class})
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @NotNull
    @JoinColumn(name = "vehicle_id")
    @ManyToOne
    private Vehicle vehicle;

    @Size(min = 1, message = "Please assign at least one employee")
    @ManyToMany
    @JoinTable(name = "orders_employees", joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> employees;

    @PrePersist
    public void create() {
        created = LocalDate.now();
    }
}
