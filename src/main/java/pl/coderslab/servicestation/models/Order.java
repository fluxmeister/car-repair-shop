package pl.coderslab.servicestation.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "title")
    private String title;

    @NotBlank
    @Column(name = "initial_diagnosis")
    private String initialDiagnosis;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "planned_repair_start")
    private LocalDate plannedRepairStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "actual_repair_start")
    private LocalDate actualRepairStart;

    @Column(name = "ordered_parts")
    private String orderedParts;

    @Column(name = "costs_of_parts")
    private Double costsOfParts;

    @Column(name = "price_of_service")
    private Double priceOfService;

    @Column(name = "created")
    private LocalDate created;

    @Column(name = "updated")
    private LocalDate updated;

    @Column(name = "note")
    private String note;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @NotNull
    @JoinColumn(name = "vehicle_id")
    @ManyToOne
    private Vehicle vehicle;

    @ManyToMany
    private List<Employee> employees;

    @PrePersist
    public void create() {
        created = LocalDate.now();
    }

    @PreUpdate
    public void update() {
        updated = LocalDate.now();
    }
}
