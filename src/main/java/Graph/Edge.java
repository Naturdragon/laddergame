package Graph;

public class Edge<T> {
    private T Source; // Vertex of the edge
    private T Target; // Vertex of the edge
    private T Weight; // Generic Variable where every thing can be stored attched to the edge


    // Base Edge with a sorce and a destination
    public Edge(T data1, T data2)
    {
        Source = data1;
        Target = data2;
    }

    // Creates an Edge with a weight
    public Edge(T source, T target, T weight)
    {
        Source = source;
        Target = target;
        Weight = weight;
    }

    public T getSource()
    {
        return Source;
    }

    public void setSource(T source)
    {
        Source = source;
    }

    public T getTarget()
    {
        return Target;
    }

    public void setTarget(T target)
    {
        Target = target;
    }

    public T getWeight()
    {
        return Weight;
    }

    public void setWeight(T weight)
    {
        Weight = weight;
    }
}
