package com.gj.miclrn.utility;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Messages implements Serializable {

	private static final long serialVersionUID = 0;

	private static final Map<String, Messages> TYPES = new LinkedHashMap<String, Messages>();
	private static final Map<String, Messages> CODES = new LinkedHashMap<String, Messages>();

	public static final Messages AEMC_VALUE_MISSING_ERR = new Messages(MessageType.ERROR, "AEMC_VALUE_MISSING_ERR",
			"AE-ERR30002", "Value(s) are missing for required fields : ${field}");

	public static Messages getInstance(final String type) {
		return TYPES.get(type);
	}

	public static Messages getInstanceByCode(final String code) {
		return CODES.get(code);
	}

	private String type;
	private String code;
	private String text;

	private MessageType messageType;

	public Messages() {
		// do nothing
	}

	public Messages(final MessageType messageType, final String type, final String code, final String text) {
		this.text = text;
		this.messageType = messageType;
		setType(type);
		setCode(code);

	}

	public String getType() {
		return type;
	}

	public String getText() {
		// return MessageProvider.getMessageText(code, MessageType.ERROR.getType(),
		// text);
		return text;
	}

	public String getCode() {
		return code;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	private void setType(final String type) {
		this.type = type;
		if (!TYPES.containsKey(type)) {
			TYPES.put(type, this);
		}
	}

	private void setCode(final String code) {
		this.code = code;
		if (!CODES.containsKey(code)) {
			CODES.put(code, this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Messages other = (Messages) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}