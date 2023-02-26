package nextstep.subway.infra;

import nextstep.subway.domain.Line;
import nextstep.subway.domain.Path;
import nextstep.subway.domain.PathFinder;
import nextstep.subway.domain.Station;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;

public class DijkstraPathFinder implements PathFinder {

    private DijkstraShortestPath dijkstraShortestPath;

    private DijkstraPathFinder(DijkstraShortestPath dijkstraShortestPath) {
        this.dijkstraShortestPath = dijkstraShortestPath;
    }

    public static PathFinder create(List<Line> lines) {
        WeightedMultigraph<Station, DefaultWeightedEdge> graph
                = new WeightedMultigraph(DefaultWeightedEdge.class);

        lines.stream().flatMap(it -> it.getStations().stream())
                .forEach(it -> graph.addVertex(it));
        lines.stream().flatMap(it -> it.getSections().stream())
                .forEach(it -> graph.setEdgeWeight(graph.addEdge(it.getUpStation(), it.getDownStation()), it.getDistance().getValue()));

        DijkstraShortestPath dijkstraShortestPath
                = new DijkstraShortestPath(graph);

        return new DijkstraPathFinder(dijkstraShortestPath);
    }

    @Override
    public Path findShortestPath(Station source, Station target) {
        List<Station> shortestPath = dijkstraShortestPath.getPath(source, target).getVertexList();
        int shortestDistance = (int) dijkstraShortestPath.getPathWeight(source, target);

        return Path.create(shortestPath, shortestDistance);
    }
}
