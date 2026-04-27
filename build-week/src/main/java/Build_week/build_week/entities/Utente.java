package Build_week.build_week.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"accountNonExpired", "accountNonLocked", "authorities", "credentialsNonExpired", "enabled"})

public class Utente implements UserDetails {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column
    private String avatar;

    @Column(nullable = false)
    private String ruoli;

    public Utente(String username, String email, String password, String nome, String cognome) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.avatar = "https://ui-avatars.com/api/?name=" + nome + "+" + cognome;
        this.ruoli = "ROLE_USER";
    }

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(ruoli.split(","))
                .map(String::trim)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
