package superapp.boundary.menu;

import java.util.ArrayList;

public class Instruction {

    private String instruction;

    public Instruction() {
    }

    public Instruction(String instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }


    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public static ArrayList<Instruction> fromObjectInstructionToInstructions(Object instructionObject){
        ArrayList<Instruction> result = new ArrayList<>();
        if(instructionObject instanceof ArrayList<?> instructionsList){
            for (Object instruction : instructionsList) {
                result.add(new Instruction((String) instruction));
            }
        }
        return result;
    }
}
