package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "follows")
@NamedQueries({

    @NamedQuery(
            name = "checkFollow",
            query = "SELECT COUNT(f) FROM Follow AS f WHERE f.follow = :follow AND f.followed = :followed"
            ),

})

@Entity
public class Follow {
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;


   @ManyToOne
   @JoinColumn(name = "follow_id")
   private Employee follow;

   @ManyToOne
   @JoinColumn(name = "followed_id")
   private Employee followed;



public Integer getId() {
    return id;
}

 public void setId(Integer id) {
    this.id = id;
}


public Employee getFollow() {
    return getFollow();
}

public void setFollow(Employee follow) {
    this.follow = follow;
}


public Employee getFollowed() {
    return followed;
}

public void setFollowed(Employee followed) {
    this.followed = followed;
}








}
