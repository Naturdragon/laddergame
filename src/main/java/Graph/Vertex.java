package Graph;

public class Vertex<T> {
    T Data;

    public Vertex(T data)
    {
        Data = data;
    }

    public void setData(T data)
    {
        Data = data;
    }

    public T getData()
    {
        return Data;
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other) return true;
        if (this == null || this.getClass() != other.getClass()) return false;

        Vertex<?> obj = (Vertex<?>) other;
        return Data.equals(obj.Data) || (Data == null && obj.Data == null);

    }

    @Override
    public int hashCode()
    {
        return Data != null ? Data.hashCode() : 0;
    }
}
