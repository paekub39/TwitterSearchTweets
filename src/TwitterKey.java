
public class TwitterKey {
	private String customerKey, customerSecret, tokenKey, tokenSecret;
	
	public TwitterKey(String customerKey, String customerSecret, String tokenKey, String tokenSecret) {
		this.customerKey = customerKey;
		this.customerSecret = customerSecret;
		this.tokenKey = tokenKey;
		this.tokenSecret = tokenSecret;
	}

	public String getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}

	public String getCustomerSecret() {
		return customerSecret;
	}

	public void setCustomerSecret(String customerSecret) {
		this.customerSecret = customerSecret;
	}

	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
}
