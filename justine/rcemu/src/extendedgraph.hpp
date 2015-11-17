#include <graph.hpp>

namespace justine{
namespace sampleclient{

class ExtendedGraph :public Graph{
public:
	using GraphNodeID = osmium::unsigned_object_id_type;
	using NodeRefGraph = boost::adjacency_list<boost::listS, boost::vecS, boost::directedS,boost::property<boost::vertex_name_t, GraphNodeID>,boost::property<boost::edge_weight_t, int>>;
	using NRGVertex = boost::graph_traits<NodeRefGraph>::vertex_descriptor;
	using NRGVertexIter = boost::graph_traits<NodeRefGraph>::vertex_iterator;
	using NRGEdge = boost::graph_traits<NodeRefGraph>::edge_descriptor;
	using NRGEdgeIter = boost::graph_traits<NodeRefGraph>::edge_iterator;
	using NRGAdjacentVertexIter = boost::graph_traits<NodeRefGraph>::adjacency_iterator;
	using VertexNameMap = boost::property_map<NodeRefGraph, boost::vertex_name_t>::type;
	using VertexIndexMap = boost::property_map<NodeRefGraph, boost::vertex_index_t>::type;
	using PredecessorMap = boost::iterator_property_map <NRGVertex*, VertexIndexMap, NRGVertex, NRGVertex&>;
	using DistanceMap = boost::iterator_property_map <int*, VertexIndexMap, int, int&>;
	using EdgeWeightMap = boost::property_map<NodeRefGraph, boost::edge_weight_t>::type;


	ExtendedGraph (const char* shm_segment) : Graph(shm_segment)
	{
		ElliminateCircles();
	}

	~ExtendedGraph(){}

	void ElliminateCircles(void);

	std::pair<NRGVertexIter, NRGVertexIter> getVertices(void);

	std::pair<NRGEdgeIter, NRGEdgeIter> getEdges(void);

	VertexIndexMap getVertexIndexMap(void);

	VertexNameMap getVertexNameMap(void);

	NRGVertex findObject(GraphNodeID id);

	std::pair<NRGAdjacentVertexIter, NRGAdjacentVertexIter> getAdjacentVertices(NRGVertex v);

	double getDistance(GraphNodeID a, GraphNodeID b);

	double pathLength(std::vector<GraphNodeID> path, int accuracy = 10);

	std::vector<GraphNodeID> DetermineDijkstraPath(NRGVertex from, NRGVertex to);

	std::vector<GraphNodeID> operator+ (std::vector<GraphNodeID> path); //delete last, se no dupes



};
}
}