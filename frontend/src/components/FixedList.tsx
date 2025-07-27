import { useFixedExtensions } from '@/hooks/useFixedExtensions'
import type { FixedExtension } from '@/types'

export const FixedList = () => {
  const { fixedList, loading, error, toggle } = useFixedExtensions()
  const list = fixedList as FixedExtension[]

  if (loading) return <p>로딩 중...</p>
  if (error) return <p>에러 발생: {error}</p>

  return (
    <div>
      <h2 className="text-xl font-bold mb-2">고정 확장자</h2>
      <div className="flex flex-wrap gap-x-6 gap-y-3 ml-0 pl-0 list-none">
        {fixedList.map((item) => (
          <li key={item.id} className="flex items-center space-x-2">
            <input
              type="checkbox"
              checked={!!item.checked}
              onChange={() => toggle(item.id)}
            />
            <span className="text-sm">{item.name}</span>
          </li>
        ))}
      </div>
    </div>
  )
}
