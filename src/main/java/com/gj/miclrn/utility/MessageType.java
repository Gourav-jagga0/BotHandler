package com.gj.miclrn.utility;

	public enum MessageType {
		ERROR("ERROR"), WARNING("WARNING"), INFO("INFO"), NOTIFICATION("NOTIFICATION"), CONFIRMATION("CONFIRMATION");
		private String messageType;

		private MessageType(String messageType) {
			this.messageType = messageType;
		}

		public String toString() {
			return messageType;
		}
	}
