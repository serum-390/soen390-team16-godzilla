package ca.serum390.godzilla.domain.orders;

import org.springframework.data.annotation.Id;

import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;

@Data
@With
@Builder
@ToString
@Table("contact")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SalesContact {
    @Id
    private Integer id;
    private String companyName;
    private String contactName;
    private String address;
    private String contact;
    private String contactType;

}
