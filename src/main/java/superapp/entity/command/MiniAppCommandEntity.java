package superapp.entity.command;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "MiniAppCommand")
public class MiniAppCommandEntity {

	@Id
	private CommandId commandId;
	private String command;
	private TargetObject targetObject;
	private Date invocationTimestamp;
	private InvokedBy invokedBy;
	private Map<String, Object> commandAttributes;

	public MiniAppCommandEntity() {
		this.commandAttributes = new TreeMap<>();
	}

	public MiniAppCommandEntity(CommandId commandId, String command, TargetObject targetObject,
			Date invocationTimestamp, InvokedBy invokedBy, Map<String, Object> commandAttributes) {
		super();
		this.commandId = commandId;
		this.command = command;
		this.targetObject = targetObject;
		this.invocationTimestamp = new Date(); // updated here ??
		this.invokedBy = invokedBy;
		this.commandAttributes = commandAttributes;
	}

	@Id
	public CommandId getCommandId() {
		return commandId;
	}

	public void setCommandId(CommandId commandId) {
		this.commandId = commandId;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public TargetObject getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(TargetObject targetObject) {
		this.targetObject = targetObject;
	}

	public Date getInvocationTimestamp() {
		return invocationTimestamp;
	}

	public void setInvocationTimestamp(Date invocationTimestamp) {
		this.invocationTimestamp = invocationTimestamp;
	}

	public InvokedBy getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(InvokedBy invokedBy) {
		this.invokedBy = invokedBy;
	}

	public Map<String, Object> getCommandAttributes() {
		return commandAttributes;
	}

	public void setCommandAttributes(Map<String, Object> commandAttributes) {
		this.commandAttributes = commandAttributes;
	}

	@Override
	public String toString() {
		return "MiniAppCommandEntity ["
				+ "commandId=" + commandId.toString()
				+ ", command=" + command 
				+ ", targetObject=" + targetObject.toString()
				+ ", invocationTimestamp=" + invocationTimestamp 
				+ ", invokedBy=" + invokedBy.toString()
				+ ", commandAttributes=" + commandAttributes.toString()
				+ "]";
	}
}