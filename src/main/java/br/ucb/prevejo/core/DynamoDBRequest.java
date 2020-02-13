package br.ucb.prevejo.core;

import br.ucb.prevejo.core.interfaces.ItemEntityParser;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.internal.IteratorSupport;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.util.ArrayList;
import java.util.List;

public class DynamoDBRequest {
    
    private Table table;
    
    public DynamoDBRequest(Table table) {
        this.table = table;
    }
    
    public <T> List<T> query(QuerySpec spec, ItemEntityParser<T> parser) {
        ItemCollection<QueryOutcome> result = this.table.query(spec);

        IteratorSupport<Item, QueryOutcome> iterator = result.iterator();

        List<T> lista = new ArrayList<>();

        while (iterator.hasNext()) {
            lista.add(parser.parse(iterator.next()));
        }

        return lista;
    }
    
}
