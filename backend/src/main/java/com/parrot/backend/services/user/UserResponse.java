package com.parrot.backend.services.user;

import com.parrot.backend.entities.User;
import lombok.Data;

import java.util.HashSet;
import java.util.UUID;
@Data
public class UserResponse {
  public UUID id;
  public String name;
  public String email;
  private String photoUrl;
  private HashSet<UUID> followers;
  private HashSet<UUID> following;

  public UserResponse(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.photoUrl = user.getPhotoUrl();
    this.followers = user.getFollowers();
    this.following = user.getFollowing();
  }
}
