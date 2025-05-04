package edu.matc.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Keys{

	@JsonProperty("keys")
	private List<KeysItem> keys;

	public List<KeysItem> getKeys(){
		return keys;
	}
}