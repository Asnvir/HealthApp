package superapp.boundary;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import superapp.entity.command.TargetObject;
import superapp.entity.user.UserId;
import superapp.entity.command.InvokedBy;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.entity.command.ObjectId;
import superapp.entity.command.CommandId;

public class MiniAppCommandBoundary {
	private CommandId commandId;
	private String command;
	private TargetObject targetObject;
	private Date invocationTimestamp;
	private InvokedBy invokedBy;
	private Map<String, Object> commandAttributes;

	public MiniAppCommandBoundary() {
		this.commandAttributes = new TreeMap<>();
	}

	public MiniAppCommandBoundary(CommandId commandId, String command, TargetObject targetObject,
			Date invocationTimestamp, InvokedBy invokedBy, Map<String, Object> commandAttributes) {
		super();
		this.commandId = commandId;
		this.command = command;
		this.targetObject = targetObject;
		this.invocationTimestamp = invocationTimestamp;
		this.invokedBy = invokedBy;
		this.commandAttributes = commandAttributes;
	}

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
		return "MiniAppCommandBoundary [commandId=" + commandId + ", command=" + command + ", targetObject="
				+ targetObject + ", invocationTimestamp=" + invocationTimestamp + ", invokedBy=" + invokedBy
				+ ", commandAttributes=" + commandAttributes + "]";
	}
	
	public MiniAppCommandEntity toEntity(String superAppName) {
		MiniAppCommandEntity newEntity = new MiniAppCommandEntity();
		CommandId newDefaultCommandId = new CommandId(superAppName, "dummyApp");
		newDefaultCommandId.setID(UUID.randomUUID() + "");
		newEntity.setCommandId(newDefaultCommandId);
		newEntity.setCommandAttributes(commandAttributes);
		newEntity.setCommand(command);
		newEntity.setInvocationTimestamp(invocationTimestamp);
		newEntity.setInvokedBy(new InvokedBy(new UserId(superAppName, invokedBy.getUserId().getEmail())));
		newEntity.setTargetObject(new TargetObject(new ObjectId(superAppName, UUID.randomUUID() + "")));
		return newEntity;
	}

}