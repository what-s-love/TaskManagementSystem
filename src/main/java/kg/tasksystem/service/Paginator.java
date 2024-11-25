package kg.tasksystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class Paginator<T> {

    public Page<T> toPage(List<T> items, Pageable pageable) {
        if (pageable.getOffset() >= items.size()){
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize() > items.size() ?
                items.size() : pageable.getOffset() + pageable.getPageSize()));
        List<T> subList = items.subList(startIndex, endIndex);
        return new PageImpl<>(subList, pageable, items.size());

    }
}
