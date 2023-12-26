package Graph;

public class Edge<T> {
    T Source; // Vertex of the edge
    T Target; // Vertex of the edge

    T Weigth; // Generic Variable where every thing can be stored attched to the edge

    // Base Edge with a sorce and a destination
    public Edge(T data1, T data2)
    {
        Source = data1;
        Target = data2;
    }

    // Creates an Edge with a weight
    public Edge(T source, T target, T weigth)
    {
        Source = source;
        Target = target;
        Weigth = weigth;
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

    public T getWeigth()
    {
        return Weigth;
    }

    public void setWeigth(T weigth)
    {
        Weigth = weigth;
    }
}
