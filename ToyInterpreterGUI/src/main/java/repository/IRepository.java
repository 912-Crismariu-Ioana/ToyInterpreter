package repository;

import model.ProgramState;
import model.dataStructures.IList;

import java.util.List;


public interface IRepository {
    void addProgram(ProgramState newProgram);

    List<ProgramState> getProgramList();

    void setProgramList(List<ProgramState> new_progs);

    void logProgramState(ProgramState prog) throws Exception;
}
