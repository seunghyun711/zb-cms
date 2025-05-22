package com.zb.cms.order.querydsl;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.zb.cms.order.domain.model.Product;
import com.zb.cms.order.domain.model.ProductItem;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -1044808339L;

    public static final QProduct product = new QProduct("product");

    public final QBaseEntity _super = new QBaseEntity(String.valueOf(this));

    //inherited
    public final DateTimePath<LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final ListPath<ProductItem, QProductItem> productItems = this.<ProductItem, QProductItem>createList("productItems", ProductItem.class, QProductItem.class, PathInits.DIRECT2);

    public final NumberPath<Long> sellerId = createNumber("sellerId", Long.class);

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}
