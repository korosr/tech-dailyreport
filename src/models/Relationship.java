package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "relationships")
@Entity
public class Relationship {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	//フォローされている人のID
	@Column(name = "followed_id", nullable = false)
	private int followed_id;

	//フォローしている人のID
	@Column(name = "follower_id", nullable = false)
	private int follower_id;
}