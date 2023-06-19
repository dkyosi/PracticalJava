package com.practicalinterview.datalayer;

import com.practicalinterview.enums.UserRoles;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"roleName"})
})
public class Role extends Base{
    @Id
    @SequenceGenerator(name = "role_sequence_generator",
            sequenceName = "role_sequence_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence_generator")
    private long roleId;
    @Enumerated(EnumType.STRING)
    private UserRoles roleName;

    public Role(UserRoles roleName) {
        this.roleName = roleName;
    }
}