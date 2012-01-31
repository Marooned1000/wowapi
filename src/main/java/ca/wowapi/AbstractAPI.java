package ca.wowapi;

import org.json.JSONException;
import org.json.JSONObject;

import ca.wowapi.exceptions.InvalidApplicationSignatureException;
import ca.wowapi.exceptions.NotModifiedException;
import ca.wowapi.exceptions.TooManyRequestsException;
import ca.wowapi.utils.APIConnection;

public class AbstractAPI {

	public static final String REGION_US = "us";
	public static final String REGION_EU = "eu";

	private String publicKey;
	private String privateKey;

	public AbstractAPI() {

	}

	public AbstractAPI(String publicKey, String privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	public JSONObject getJSONFromRequest(String url) throws NotModifiedException, InvalidApplicationSignatureException, TooManyRequestsException {
		return this.getJSONFromRequest(url, 0);
	}

	public JSONObject getJSONFromRequest(String url, long lastModifiedDate) throws NotModifiedException, InvalidApplicationSignatureException, TooManyRequestsException {
		JSONObject jsonobject;

		String str = null;
		if (null != publicKey && null != privateKey) {
			str = APIConnection.getStringJSONFromRequestAuth(url, publicKey, privateKey, lastModifiedDate);
		} else {
			str = APIConnection.getStringJSONFromRequest(url, lastModifiedDate);
		}

		try {
			jsonobject = new JSONObject(str);

			if (null != jsonobject && jsonobject.has("status")) {
				if (jsonobject.getString("status").equalsIgnoreCase("nok")) {
					if (jsonobject.getString("reason").equalsIgnoreCase("Invalid application signature.")) {
						throw new InvalidApplicationSignatureException();
					} else if (jsonobject.getString("reason").contains("too many requests") || jsonobject.getString("reason").contains("Daily limit exceeded")) {
						throw new TooManyRequestsException();
					}
				}

			}
			return jsonobject;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String encode(String value) {
		try {
			return java.net.URLEncoder.encode(value, "UTF-8").replace("+", "%20");
		} catch (Exception e) {
			return value;
		}
	}

}
