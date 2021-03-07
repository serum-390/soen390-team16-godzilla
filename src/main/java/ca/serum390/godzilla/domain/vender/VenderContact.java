package ca.serum390.godzilla.domain.vender;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@Table("contact")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class VenderContact {
    @Id
    private Integer id;
    private String companyName;
    private String contactName;
    private String address;
    private String contact;
    private String contactType;
}
