import { useEffect, useMemo, useState } from 'react';
import PostList from './components/PostList.jsx';
import PostForm from './components/PostForm.jsx';
import './App.css';

const API_BASE = '/api/posts';

export default function App() {
  const [posts, setPosts] = useState([]);
  const [selectedPost, setSelectedPost] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const sortedPosts = useMemo(
    () => [...posts].sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt)),
    [posts]
  );

  useEffect(() => {
    fetchPosts();
  }, []);

  async function fetchPosts() {
    setLoading(true);
    setError('');
    try {
      const response = await fetch(API_BASE);
      if (!response.ok) {
        throw new Error('게시글을 불러오지 못했습니다.');
      }
      const data = await response.json();
      setPosts(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  async function handleCreate(post) {
    setError('');
    try {
      const response = await fetch(API_BASE, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(post)
      });
      if (!response.ok) {
        const message = await extractError(response);
        throw new Error(message || '게시글을 등록하지 못했습니다.');
      }
      await fetchPosts();
      setSelectedPost(null);
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleUpdate(post) {
    if (!selectedPost) return;
    setError('');
    try {
      const response = await fetch(`${API_BASE}/${selectedPost.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(post)
      });
      if (!response.ok) {
        const message = await extractError(response);
        throw new Error(message || '게시글을 수정하지 못했습니다.');
      }
      await fetchPosts();
      setSelectedPost(null);
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleDelete(id) {
    setError('');
    try {
      const response = await fetch(`${API_BASE}/${id}`, {
        method: 'DELETE'
      });
      if (!response.ok) {
        const message = await extractError(response);
        throw new Error(message || '게시글을 삭제하지 못했습니다.');
      }
      setPosts((prev) => prev.filter((post) => post.id !== id));
      if (selectedPost?.id === id) {
        setSelectedPost(null);
      }
    } catch (err) {
      setError(err.message);
    }
  }

  async function extractError(response) {
    try {
      const data = await response.json();
      if (data.errors) {
        return Object.values(data.errors).join(' ');
      }
      return data.message || '';
    } catch (error) {
      try {
        return await response.text();
      } catch (err) {
        return '';
      }
    }
  }

  function handleSelect(post) {
    setSelectedPost(post);
  }

  function handleCancelEdit() {
    setSelectedPost(null);
  }

  return (
    <div className="app">
      <header className="app__header">
        <h1>SimpleBBS</h1>
        <p>Spring Boot와 React로 구현한 간단한 게시판입니다.</p>
      </header>

      <main className="app__content">
        <section className="app__section">
          <h2>게시글 목록</h2>
          {loading && <p>불러오는 중...</p>}
          {error && <p className="app__error">{error}</p>}
          <PostList posts={sortedPosts} onSelect={handleSelect} onDelete={handleDelete} />
        </section>

        <section className="app__section">
          <h2>{selectedPost ? '게시글 수정' : '새 게시글 작성'}</h2>
          <PostForm
            key={selectedPost?.id ?? 'new'}
            initialValue={selectedPost}
            onSubmit={selectedPost ? handleUpdate : handleCreate}
            onCancel={selectedPost ? handleCancelEdit : undefined}
          />
        </section>
      </main>
    </div>
  );
}
