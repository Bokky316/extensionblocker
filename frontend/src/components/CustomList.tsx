import { useCustomExtensions } from '@/hooks/useCustomExtensions'

export const CustomList = () => {
  const { customList, loading, removeExtension } = useCustomExtensions()

  if (loading) return <p>로딩 중...</p>

  return (
    <div>
      <h2 className="text-xl font-bold mb-2">커스텀 확장자</h2>
      <ul className="space-y-1">
        {customList.map((item) => (
          <li key={item.id} className="flex justify-between items-center">
            <span>{item.name}</span>
            <button
              onClick={() => removeExtension(item.id)}
              className="text-red-500 hover:underline"
            >
              X
            </button>
          </li>
        ))}
      </ul>
    </div>
  )
}
