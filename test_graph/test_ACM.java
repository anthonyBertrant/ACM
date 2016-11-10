package test_graph;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import javax.swing.JFrame;

/**
 * Created by anthonybertrant on 03/11/2016.
 */
public class test_ACM extends JFrame{

    private static final long serialVersionUID = -2707712944901661771L;

    public test_ACM()
    {
        super("Hello, World!");

        String noArrow = mxConstants.STYLE_ENDARROW + "=none";

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try
        {
            Object v1 = graph.insertVertex(parent, null, "1", 100, 20, 80,30); //
            Object v2 = graph.insertVertex(parent, null, "2", 200, 70, 80, 30); //
            Object v3 = graph.insertVertex(parent, null, "3", 100, 250, 80, 30); //
            Object v4 = graph.insertVertex(parent, null, "4", 200, 150, 80, 30); //
            Object v5 = graph.insertVertex(parent, null, "5", 20, 70, 80, 30); //

            graph.insertEdge(parent, null, "2", v1, v2,noArrow);
            graph.insertEdge(parent, null, "1", v1, v3,noArrow);
            graph.insertEdge(parent, null, "5", v1, v5,noArrow);
            graph.insertEdge(parent, null, "2", v2, v3,noArrow);
            graph.insertEdge(parent, null, "16", v5, v3,noArrow);
            graph.insertEdge(parent, null, "6", v3, v4,noArrow);
            graph.insertEdge(parent, null, "9", v4, v2,noArrow);
        }
        finally
        {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }

    public static void main(String[] args){
        test_ACM frame = new test_ACM();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);
    }
}
