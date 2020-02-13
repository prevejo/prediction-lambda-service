package br.ucb.prevejo.core.interfaces;

import com.amazonaws.services.dynamodbv2.document.Item;

public interface ItemEntityParser<T> {
    T parse(Item item);
}
