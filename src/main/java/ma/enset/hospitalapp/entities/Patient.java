package ma.enset.hospitalapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Entity//pour dire que c'est une entité JPA (pour la persistance) qui sert à mapper une table dans la BD cad créer une table dans la BD
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty @Size(min = 4, max = 20)
    private String nom;
    @Temporal(TemporalType.DATE)//@Temporal pour dire que c'est une date sous forme de date + heure + minute + seconde et non pas une date sous forme de timestamp
    private Date dateNaissance;
    private boolean malade;
    @Min(10)//pour dire que la valeur de score doit être supérieure ou égale à 10
    private int score;
}
