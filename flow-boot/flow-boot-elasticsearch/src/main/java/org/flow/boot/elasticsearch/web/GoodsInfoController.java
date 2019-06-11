package org.flow.boot.elasticsearch.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.flow.boot.common.Response;
import org.flow.boot.elasticsearch.entity.GoodsInfo;
import org.flow.boot.elasticsearch.repository.GoodsRepository;
import org.flow.boot.elasticsearch.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
public class GoodsInfoController {

    @Autowired
    private GoodsRepository goodsRepository;

    private SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1, 1);

    @PostMapping("save")
    public Response<GoodsInfo> save(GoodsInfo entity) {
        entity.setId(0L);

        goodsRepository.save(entity);

        return Response.okResponse(entity);
    }

    @PutMapping("/update")
    public Response<GoodsInfo> update(GoodsInfo entity) {

        goodsRepository.save(entity);

        return Response.okResponse(entity);
    }

    @DeleteMapping("/delete")
    public Response<Void> delete(long id) {
        goodsRepository.deleteById(id);
        return Response.okResponse();
    }

    @DeleteMapping("/delete/all")
    public Response<Void> deleteAll() {
        goodsRepository.deleteAll();
        return Response.okResponse();
    }

    @GetMapping("/detail")
    public Response<GoodsInfo> detail(long id) {
        return Response.okResponse(goodsRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @GetMapping("/list")
    public Response<List<GoodsInfo>> list() {
        List<GoodsInfo> list = new ArrayList<>();
        goodsRepository.findAll().forEach(list::add);
        return Response.okResponse(list);
    }

    private static final Integer SIZE = 300;

    @GetMapping("test")
    public String test() throws IOException {
        String file = "xyj.txt";
        String path = GoodsInfoController.class.getClassLoader().getResource(file).getPath();
        System.out.println(path);

        List<GoodsInfo> list = new LinkedList<>();
        Files.readAllLines(Paths.get(path.substring(1))).parallelStream().forEach(line -> {
            for (int i = 0; i < line.length();) {
                GoodsInfo entity = new GoodsInfo();
                entity.setId(idWorker.nextId());
                entity.setName(i < line.length() - 8 ? line.substring(i, i + 3) : line.substring(i));
                entity.setDescription(i < line.length() - 8 ? line.substring(i, i + 8) : line.substring(i));
                i += 8;
                list.add(entity);
            }
            // if (list.isEmpty() == false) {
            // goodsRepository.saveAll(list);
            // }
        });

        int limit = list.size();
        System.out.println(limit);
        long n1 = System.nanoTime();
        // 方法一：使用流遍历操作
        List<List<GoodsInfo>> mglist = new ArrayList<>();
        Stream.iterate(0, n -> n + SIZE).limit(limit).forEach(i -> {
            mglist.add(list.stream().skip(i * SIZE).limit(SIZE).collect(Collectors.toList()));
        });
        System.out.println((System.nanoTime() - n1) / (1000 * 1000));
        n1 = System.nanoTime();
        // 方法二：获取分割后的集合
        List<List<GoodsInfo>> splitList = Stream.iterate(0, n -> n + SIZE).limit(limit).parallel()
                .map(a -> list.stream().skip(a * SIZE).limit(SIZE).parallel().collect(Collectors.toList()))
                .collect(Collectors.toList());
        System.out.println((System.nanoTime() - n1) / (1000 * 1000));
        System.out.println("==============================================");
        System.out.println(splitList.size());
        return "测试";
    }

}
