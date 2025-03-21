package co.za.bsg.domain.model.api;

import java.util.List;

public class AnagramCounterResponse {
    private List<AnagramCounter> anagramCounterList;
    private Long algorithmExecutionTime;

    public List<AnagramCounter> getAnagramCounterList() {
        return anagramCounterList;
    }

    public AnagramCounterResponse setAnagramCounterList(List<AnagramCounter> anagramCounterList) {
        this.anagramCounterList = anagramCounterList;
        return this;
    }

    public Long getAlgorithmExecutionTime() {
        return algorithmExecutionTime;
    }

    public AnagramCounterResponse setAlgorithmExecutionTime(Long algorithmExecutionTime) {
        this.algorithmExecutionTime = algorithmExecutionTime;
        return this;
    }

    @Override
    public String toString() {
        return "AnagramCounterResponse{" +
                "anagramCounterList=" + anagramCounterList +
                ", algorithmExecutionTime=" + algorithmExecutionTime +
                '}';
    }
}
