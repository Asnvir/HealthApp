package superapp.boundary.recipe;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

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
        ObjectMapper objectMapper = new ObjectMapper();
        List<Instruction> instructions = objectMapper.convertValue(instructionObject, new TypeReference<>() {});
        return new ArrayList<>(instructions);
    }
}
