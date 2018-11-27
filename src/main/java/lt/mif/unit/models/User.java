package lt.mif.unit.models;


public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private boolean locked;
    private boolean deleted;

    public User() {

    }

    public User(Long id, String name, String email, String password, boolean locked, boolean deleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.locked = locked;
        this.deleted = deleted;
    }

    public static User newUser(String name, String email, String password) {
        return new User(null, name, email, password, false, false);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Two user objects are equal if they share the same id.
     *
     * @param obj other user object
     * @return true if other user has the same id.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
        return other.getId().equals(getId());
    }

    @Override
    public String toString() {
        return String.format("User{%d : '%s'}", getId(), getName());
    }

    @Override
    public int hashCode() {
        return Math.toIntExact(getId());
    }
}
