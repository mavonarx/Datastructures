pub trait Heap<T> {
    fn push(&mut self, item: T);
    fn clear(&mut self);
    fn peek(&self) -> Option<&T>;
    fn get_size(&self) -> usize;
    fn is_empty(&self) -> bool;
    fn poll(&mut self) -> Option<T>;
}

pub struct BinaryHeap<T> {
    data: Vec<T>,
    is_min_heap: bool,
}

impl<T: std::cmp::Ord> BinaryHeap<T> {
    pub fn new(min: bool) -> Self {
        BinaryHeap::<T> {
            data: Vec::<T>::new(),
            is_min_heap: min,
        }
    }

    pub fn new_max() -> Self {
        BinaryHeap::<T> {
            data: Vec::<T>::new(),
            is_min_heap: false,
        }
    }

    pub fn new_min() -> Self {
        BinaryHeap::<T> {
            data: Vec::<T>::new(),
            is_min_heap: true,
        }
    }

    pub fn get_data(&self) -> &Vec<T> {
        &self.data
    }

    // returns the bigger child in a max heap or the smaller child in a min heap
    fn get_relevant_child(&self, position: usize) -> Option<usize> {
        let right_child = position * 2 + 2;
        let left_child = position * 2 + 1;

        // no child exists
        if left_child >= self.data.len() {
            return None;
        }

        // only the left child exists
        if right_child >= self.data.len() {
            return Some(left_child);
        }
        Some(
            if (self.is_min_heap && self.data[left_child] < self.data[right_child])
                || (!self.is_min_heap && self.data[left_child] > self.data[right_child])
            {
                left_child
            } else {
                right_child
            },
        )
    }

    fn bubble_up(&mut self, mut child: usize) {
        while child > 0 {
            let parent = (child - 1) / 2;
            if (!self.is_min_heap && self.data[child] > self.data[parent])
                || (self.is_min_heap && self.data[child] < self.data[parent])
            {
                self.data.swap(child, parent);
                child = parent;
            } else {
                break;
            }
        }
    }

    fn bubble_down(&mut self, mut parent: usize) {
        while let Some(child) = self.get_relevant_child(parent) {
            if (self.data[child] < self.data[parent] && self.is_min_heap)
                || (self.data[child] > self.data[parent] && !self.is_min_heap)
            {
                self.data.swap(parent, child);
                parent = child;
            } else {
                break;
            }
        }
    }
}

impl<T: std::cmp::Ord> Heap<T> for BinaryHeap<T> {
    fn peek(&self) -> Option<&T> {
        if self.is_empty() {
            return None;
        }
        Some(&self.data[0])
    }

    fn poll(&mut self) -> Option<T> {
        if self.is_empty() {
            return None;
        }
        let last_element: usize = self.data.len() - 1;
        self.data.swap(0, last_element);
        let result = self.data.remove(last_element);
        self.bubble_down(0);
        return Some(result);
    }
    fn clear(&mut self) {
        self.data.clear();
    }
    fn push(&mut self, item: T) {
        self.data.push(item);
        let last_child = self.data.len() - 1;
        self.bubble_up(last_child);
    }
    fn get_size(&self) -> usize {
        self.data.len()
    }
    fn is_empty(&self) -> bool {
        self.data.len() == 0
    }
}

#[cfg(test)]
mod tests {
    use super::{BinaryHeap, Heap};

    #[test]
    fn test_create_min_heap() {
        let heap = BinaryHeap::<i32>::new_min();
        assert!(heap.is_empty());
    }

    #[test]
    fn test_create_max_heap() {
        let heap = BinaryHeap::<i32>::new_max();
        assert!(heap.is_empty());
    }

    #[test]
    fn test_insert_min_heap() {
        let mut heap = BinaryHeap::new_min();
        heap.push(10);
        heap.push(5);
        heap.push(20);
        assert_eq!(heap.peek(), Some(&5)); // Min heap: smallest element should be on top
    }

    #[test]
    fn test_insert_max_heap() {
        let mut heap = BinaryHeap::new_max();
        heap.push(10);
        heap.push(5);
        heap.push(20);
        assert_eq!(heap.peek(), Some(&20)); // Max heap: largest element should be on top
    }

    #[test]
    fn test_poll_min_heap() {
        let mut heap = BinaryHeap::new_min();
        heap.push(10);
        heap.push(5);
        heap.push(20);
        assert_eq!(heap.poll(), Some(5)); // Poll smallest element
        assert_eq!(heap.poll(), Some(10)); // Poll smallest element
        assert_eq!(heap.poll(), Some(20)); // Poll smallest element
        assert!(heap.is_empty());
    }

    #[test]
    fn test_poll_max_heap() {
        let mut heap = BinaryHeap::new_max();
        heap.push(10);
        heap.push(5);
        heap.push(5);
        heap.push(3);
        heap.push(100);
        heap.push(20);
        assert_eq!(heap.poll(), Some(100)); // Poll largest element
        assert_eq!(heap.poll(), Some(20)); // Poll largest element
        assert_eq!(heap.poll(), Some(10)); // Poll largest element
        assert_eq!(heap.poll(), Some(5)); // Poll largest element
        heap.push(199);
        assert_eq!(heap.poll(), Some(199)); // Poll largest element
        assert_eq!(heap.poll(), Some(5)); // Poll largest element
        assert_eq!(heap.poll(), Some(3)); // Poll largest element
        assert!(heap.is_empty());
    }

    #[test]
    fn test_get_size() {
        let mut heap = BinaryHeap::<i32>::new_min();
        assert_eq!(heap.get_size(), 0);
        heap.push(10);
        heap.push(5);
        assert_eq!(heap.get_size(), 2);
    }

    #[test]
    fn test_clear() {
        let mut heap = BinaryHeap::new_max();
        heap.push(10);
        heap.push(20);
        heap.clear();
        assert!(heap.is_empty());
    }

    #[test]
    fn test_poll_empty_heap() {
        let mut heap = BinaryHeap::<i32>::new_min();
        assert_eq!(heap.poll(), None);
    }

    #[test]
    fn test_heap_property_after_inserts_min_heap() {
        let mut heap = BinaryHeap::<i32>::new_min();
        heap.push(3);
        heap.push(2);
        heap.push(15);
        heap.push(5);
        heap.push(4);
        heap.push(45);

        // Polling in a min-heap should return elements in increasing order
        assert_eq!(heap.poll(), Some(2));
        assert_eq!(heap.poll(), Some(3));
        assert_eq!(heap.poll(), Some(4));
        assert_eq!(heap.poll(), Some(5));
        assert_eq!(heap.poll(), Some(15));
        assert_eq!(heap.poll(), Some(45));
    }

    #[test]
    fn test_heap_property_after_inserts_max_heap() {
        let mut heap = BinaryHeap::<i32>::new_max();
        heap.push(3);
        heap.push(2);
        heap.push(15);
        heap.push(5);
        heap.push(4);
        heap.push(45);

        // Polling in a max-heap should return elements in decreasing order
        assert_eq!(heap.poll(), Some(45));
        assert_eq!(heap.poll(), Some(15));
        assert_eq!(heap.poll(), Some(5));
        assert_eq!(heap.poll(), Some(4));
        assert_eq!(heap.poll(), Some(3));
        assert_eq!(heap.poll(), Some(2));
    }
}
