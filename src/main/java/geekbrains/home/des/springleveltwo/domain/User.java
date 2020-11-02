package geekbrains.home.des.springleveltwo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users_tbl")
public class User {
    private static final String SEQUANCE_NAME = "user_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUANCE_NAME)
    @SequenceGenerator(name = SEQUANCE_NAME, sequenceName = SEQUANCE_NAME, allocationSize = 1)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name_fld")
    private String name;

    @Column(name = "password_fld")
    private String password;

    @Column(name = "email_fld")
    private String email;

    @Column(name = "archive_fld")
    private boolean archive;

    @Column(name = "role_fld")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Bucket bucket;




}
