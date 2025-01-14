import './App.css'
import SearchBar from './components/SearchBar'

function App() {
  return (
    <>
    <p className='w-2/3 text-left p-5'>
    This project implements an advanced search functionality using Redis OM (Object Mapping) and RediSearch,
    backed by Spring Boot, with a React frontend for visualization. The search system supports fuzzy matching,
    prefix searching, and exact matching capabilities.
    </p>
      <SearchBar/>
    </>
  )
}

export default App
