package com.example.theos.BordGameGraph;

import com.example.theos.GameBoard;

public class Weight <T>{
    private T data;
    private BoardGraph.edgeType type;

    public Weight(){

    }
    public Weight(T data){
        this.data = data;
    }
    public Weight(BoardGraph.edgeType type){
        this.type = type;
    }

    public Weight(T data , BoardGraph.edgeType type){
        this.data = data;
        this.type = type;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public BoardGraph.edgeType getType()
    {
        return type;
    }

    public void setType(BoardGraph.edgeType type)
    {
        this.type = type;
    }
}
