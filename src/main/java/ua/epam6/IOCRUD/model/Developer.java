package ua.epam6.IOCRUD.model;

import java.util.Set;

public class Developer {
    private Long id;
    private String name;
    private Set<Skill> skills;
    private Account account;

    public Developer(Long id, String name, Set<Skill> skills, Account account) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.account = account;
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

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id:" + id +
                ", name:'" + name + '\'' +
                ", skills:" + skills +
                ", account:" + account +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Developer developer = (Developer) o;

        if (id != null ? !id.equals(developer.id) : developer.id != null) return false;
        if (name != null ? !name.equals(developer.name) : developer.name != null) return false;
        if (skills != null ? !skills.equals(developer.skills) : developer.skills != null) return false;
        return account != null ? account.equals(developer.account) : developer.account == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (skills != null ? skills.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        return result;
    }
}
