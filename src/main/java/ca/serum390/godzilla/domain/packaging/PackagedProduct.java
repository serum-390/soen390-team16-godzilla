package ca.serum390.godzilla.domain.packaging;

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
@Table("packaged_products")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PackagedProduct {
    @Id 
    private Integer id;
    private float length;
    private float width;
    private float height;
    private float weight;
    private String packageType;  
   
}