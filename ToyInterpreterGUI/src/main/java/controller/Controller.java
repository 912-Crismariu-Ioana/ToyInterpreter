package controller;

import model.GarbageCollector;
import model.ProgramState;
import model.dataStructures.IList;
import model.dataStructures.MyList;
import model.observer.Observable;
import model.observer.Observer;
import model.values.IValue;
import model.values.ReferenceValue;
import repository.IRepository;
import repository.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements Observable {
    private IRepository repo;
    private GarbageCollector garbageCollector;
    private ExecutorService executor;
    private IList<Observer> observers;

    public Controller(IRepository repo) {
        this.repo = repo;
        this.garbageCollector = new GarbageCollector();
        this.observers = new MyList<>();
    }

    private void oneStepForAllPrograms(List<ProgramState> prgList) throws Exception {
        prgList.forEach(p -> {
            try {
                repo.logProgramState(p);
            } catch (Exception e) {
                System.out.println("Error while logging program states");
            }
        });
        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) p::oneStep)
                .collect(Collectors.toList());
        List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println("One step execution for all threads failed: " + e);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        prgList.addAll(newPrgList);
//        prgList.forEach(p-> {
//            try {
//                repo.logProgramState(p);
//            } catch (Exception e) {
//                System.out.println("Error while logging program states");
//            }
//        });
        repo.setProgramList(prgList);
    }

    public void allStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> prgList = removeCompletedPrograms(repo.getProgramList());
        while (prgList.size() > 0) {
            repo.getProgramList().stream().forEach(program -> program.getHeap().setContent(
                    garbageCollector.garbageCollector(
                            garbageCollector.addIndirectReferences(garbageCollector.addressesFromAllTables(repo.getProgramList()), program.getHeap()),
                            program.getHeap())));
            oneStepForAllPrograms(prgList);
            prgList = removeCompletedPrograms(repo.getProgramList());
        }
        executor.shutdownNow();
        repo.setProgramList(prgList);
    }


    private List<Integer> getReferencesInUse(Collection<IValue> values) {
        return values.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {
                    ReferenceValue v1 = (ReferenceValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    private List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList) {
        return inProgramList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public IRepository getRepository() {
        return repo;
    }

    public void allStepGUI() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> prgList = removeCompletedPrograms(repo.getProgramList());
        if (prgList.size() > 0) {
            repo.getProgramList().stream().forEach(program -> program.getHeap().setContent(
                    garbageCollector.garbageCollector(
                            garbageCollector.addIndirectReferences(garbageCollector.addressesFromAllTables(repo.getProgramList()), program.getHeap()),
                            program.getHeap())));
            oneStepForAllPrograms(prgList);
            this.notifyObservers();
            prgList = removeCompletedPrograms(repo.getProgramList());
        }
        executor.shutdownNow();
        repo.setProgramList(prgList);
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o: observers.getContent()){
            o.update();
        }
    }
}
