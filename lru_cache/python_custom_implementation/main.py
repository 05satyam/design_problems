class Node:
    def __init__(self, key:int, val:int):
        self.key=key
        self.val=val
        self.prev=None
        self.next=None

class LRU:
    def __init__(self, capacity:int):
        self.capacity=capacity
        self.cache=[]
        self.head=Node(0,0)
        self.tail=Node(0,0)
        self.head.next=self.tail
        self.tail.prev=self.head

    def _add(self, node:Node):
        next_node=self.head.next
        self.head.next=node
        node.prev=self.head
        node.next=next_node
        next_node.prev=node

    def _remove(self, node:Node):
        prev_node=node.prev
        next_node=node.next
        prev_node.next=next_node
        next_node.prev = prev_node

    def _find_node(self, key:int):
        for n in self.cache:
            if n.key==key:
                return n
        return None

    def get(self, key:int):
        find=self._find_node(key)
        if not find:
            return -1
        self._remove(find)
        self._add(find)
        return find.val
    def put(self, key:int, val:int):
        n=self._find_node(key)
        if n:
            self._remove(node)
            self.cache.remove(node)
        elif len(self.cache) >= self.capacity:
            lru=self.tail.prev
            self._remove(lru)
            self.cache.remove(lru)

        new_node = Node(key, val)
        self.cache.append(new_node)
        self._add(new_node)


cache=LRU(1)
cache.put(1,2)
cache.put(2, 2)
print(cache.get(1))