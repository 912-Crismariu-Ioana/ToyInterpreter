package model;

import model.dataStructures.IHeap;
import model.values.IValue;
import model.values.ReferenceValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GarbageCollector {
    public Map<Integer, IValue> garbageCollector(List<Integer> symTableAddresses, IHeap<IValue> heap) {
        Set<Map.Entry<Integer, IValue>> heapSet = heap.entrySet();

        return heapSet.stream()
                .filter(e -> symTableAddresses.contains(e.getKey()))
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> addressesFromAllTables(List<ProgramState> progs) {
        return progs.stream()
                .flatMap(progState -> progState.getSymTable().values().stream())
                .collect(Collectors.toList())
                .stream()
                .filter(elem -> elem instanceof ReferenceValue)
                .map(element -> ((ReferenceValue) element).getAddress())
                .collect(Collectors.toList());
    }

    public List<Integer> addIndirectReferences(List<Integer> addressesFromSymTable, IHeap<IValue> heapTable) {
        boolean change = true;
        Set<Map.Entry<Integer, IValue>> heapSet = heapTable.entrySet();
        List<Integer> newAddressList = new ArrayList<>(addressesFromSymTable);
        while (change) {
            List<Integer> appendingList = null;
            change = false;
            appendingList = heapSet.stream()
                    .filter(e -> e.getValue() instanceof ReferenceValue)
                    .filter(e -> newAddressList.contains(e.getKey()))
                    .map(e -> (((ReferenceValue) e.getValue()).getAddress()))
                    .filter(e -> !newAddressList.contains(e))
                    .collect(Collectors.toList());
            if (!appendingList.isEmpty()) {
                change = true;
                newAddressList.addAll(appendingList);
            }
        }
        return newAddressList;
    }
}

