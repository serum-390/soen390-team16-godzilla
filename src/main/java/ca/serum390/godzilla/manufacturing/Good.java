package ca.serum390.godzilla.manufacturing;

import java.util.UUID;

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
@Table("goods")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Good {

    @Id
    private UUID id;
    private String name;
    private String description;
}
