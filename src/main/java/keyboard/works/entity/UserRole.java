package keyboard.works.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_role")
public class UserRole {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "roles_authorities",
			joinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name="authority_id", referencedColumnName = "id") )
	private Set<UserAuthority> authorities;
	
	public UserRole(String name) {
		this.name = name;
	}
}
