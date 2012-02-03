package job.application;

public interface Node {
    
    Node[] getChildren();

    int getValue();
}