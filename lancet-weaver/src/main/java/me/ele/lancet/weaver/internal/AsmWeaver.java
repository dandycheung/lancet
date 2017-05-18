package me.ele.lancet.weaver.internal;

import java.util.List;
import java.util.Map;

import me.ele.lancet.weaver.ClassData;
import me.ele.lancet.weaver.MetaParser;
import me.ele.lancet.weaver.Weaver;
import me.ele.lancet.weaver.internal.asm.ClassTransform;
import me.ele.lancet.weaver.internal.entity.TotalInfo;
import me.ele.lancet.weaver.internal.graph.Graph;
import me.ele.lancet.weaver.internal.graph.Node;
import me.ele.lancet.weaver.internal.log.Log;
import me.ele.lancet.weaver.internal.parser.AsmMetaParser;


/**
 * Created by gengwanpeng on 17/3/21.
 */
public class AsmWeaver implements Weaver {

    public static Weaver newInstance(ClassLoader cl, Map<String, Node> nodesMap, List<String> classes) {
        MetaParser parser = new AsmMetaParser(cl);
        Graph graph = new Graph(nodesMap);
        TotalInfo totalInfo = parser.parse(classes, graph);
        return new AsmWeaver(totalInfo, graph);
    }

    private final TotalInfo totalInfo;
    private final Graph graph;

    private AsmWeaver(TotalInfo totalInfo, Graph graph) {
        Log.d(totalInfo.toString());
        this.graph = graph;
        this.totalInfo = totalInfo;
    }

    @Override
    public ClassData[] weave(byte[] input, String relativePath) {
        return ClassTransform.weave(totalInfo, graph, input, relativePath);
    }

}
