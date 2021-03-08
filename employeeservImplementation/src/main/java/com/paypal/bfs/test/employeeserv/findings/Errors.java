package com.paypal.bfs.test.employeeserv.findings;


public class Errors {

	private String field;
    private String message;
    
    public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    Errors(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public static ErrorBuilder builder() {
        return new ErrorBuilder();
    }

    public static class ErrorBuilder {

        private String field;
        private String message;

        ErrorBuilder() {
        }

        public ErrorBuilder field(String field) {
            this.field = field;
            return this;
        }

        public ErrorBuilder message(String message) {
            this.message = message;
            return this;
        }

        public Errors build() {
            return new Errors(field, message);
        }

        public String toString() {
            return "Errors.ErrorBuilder(field=" + this.field + ", message=" + this.message + ")";
        }
    }
}
